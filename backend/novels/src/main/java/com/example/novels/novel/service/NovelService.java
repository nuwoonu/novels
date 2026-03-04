package com.example.novels.novel.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.novels.ai.domain.response.AiDescriptionDto;
import com.example.novels.novel.dto.NovelDTO;
import com.example.novels.novel.dto.PageRequestDTO;
import com.example.novels.novel.dto.PageResultDTO;
import com.example.novels.novel.entity.Genre;
import com.example.novels.novel.entity.Novel;
import com.example.novels.novel.repository.GradeRepository;
import com.example.novels.novel.repository.NovelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.ai.chat.client.ChatClient;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;
    private final GradeRepository gradeRepository;
    private final ChatClient chatClient;

    // CRUD
    public Long create(NovelDTO dto) {
        Novel novel = Novel.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publishedDate(dto.getPublishedDate())
                .summary(dto.getSummary())
                .genre(Genre.builder().id(dto.getGid()).build())
                .available(dto.isAvailable())
                .build();

        Long id = novelRepository.save(novel).getId();
        return id;

        // 도서 등록 후
    }

    public AiDescriptionDto generateDescription(Long id) {
        // description이 없는 경우
        Novel novel = novelRepository.findById(id).get();
        if (novel.getDescription() != null && !novel.getDescription().isBlank()) {
            return new AiDescriptionDto(id, novel.getDescription());
        }

        String system = """
                당신은 출판사 마케터 겸 카피라이터입니다.
                입력으로 제공된 정보(도서명, 작가, 장르 , 줄거리)만 활용.
                줄거리/등장인물/세계관/수상경력 등 제공되지 않은 사실을 사용하지  말것
                """.stripIndent();

        String user = """
                도서명 : %s
                작가 : %s
                장르 : %s
                줄거리 : %s

                출력 규칙
                - 한국어 5부



                    """.formatted(novel.getTitle(), novel.getAuthor(), novel.getGenre().getName(), novel.getSummary());

        String aiText = chatClient.prompt().system(system).user(user).call().content();
        novel.changeDescription(aiText);
        return new AiDescriptionDto(id, aiText);
    }

    // READ
    @Transactional(readOnly = true)
    public NovelDTO getRow(Long id) {
        Object[] row = novelRepository.getNovelById(id);
        return entityToDto((Novel) row[0], (Genre) row[1], (Double) row[2]);

    }

    public PageResultDTO<NovelDTO> getList(PageRequestDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize(), Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(dto.getGenre(), dto.getKeyword(), pageable);

        Function<Object[], NovelDTO> function = en -> entityToDto((Novel) en[0], (Genre) en[1], (Double) en[2]);

        List<NovelDTO> dtoList = result.get().map(function).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).pageRequestDTO(dto).totalCount(totalCount).build();

    }

    private NovelDTO entityToDto(Novel novel, Genre genre, Double rating) {
        NovelDTO novelDTO = NovelDTO.builder()
                .id(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .available(novel.isAvailable())
                .publishedDate(novel.getPublishedDate())
                .summary(novel.getSummary())
                .description(novel.getDescription())
                .gid(genre.getId())
                .genreName(genre.getName())
                .rating(rating != null ? rating.intValue() : 0)
                .build();

        return novelDTO;
    }

    // available 변경
    public Long updateAvailable(NovelDTO dto) {
        Novel novel = novelRepository.findById(dto.getId()).get();
        novel.changeAvailable(dto.isAvailable());
        return novel.getId();

    }

    // available + 다른거 (장르)
    public Long update(NovelDTO dto) {
        Novel novel = novelRepository.findById(dto.getId()).get();

        novel.changeAvailable(dto.isAvailable());
        novel.changeGenre(Genre.builder().id(dto.getGid()).build());

        return novel.getId();
    }

    // DELETE
    public void delete(Long id) {

        // 평점삭제
        gradeRepository.deleteByNovel(id);
        // 도서삭제
        novelRepository.deleteById(id);

    }
}
