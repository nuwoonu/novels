import type React from 'react';
import { useNovels } from '../../hooks/useNovels';
import BasicLayout from '../../layouts/BasicLayout';
import { genres } from '../../utils/novelUtil';
import Error from '../common/Error';
import Loading from '../common/Loading';
import NovelList from './NovelList';
import { useSearchParams } from 'react-router-dom';

const Home = () => {
  // 파라미터 값 가져오기
  // httP://locahost:5173/?page=1&genre=1&keyword=The Hobbit
  const [searchParams, setSearchParams] = useSearchParams();

  //페이지 나누기 현재 페이지 저장용
  // const [currentPage, setcurrentPage] = useState(0);

  const genre = Number(searchParams.get('genre'));
  const keyword = searchParams.get('keyword') ?? '';
  const currentPage = Number(searchParams.get('page') ?? 0);

  const { serverData, loading, error, toggleAvailable } = useNovels(
    currentPage,
    10,
    genre,
    keyword,
  );

  // 기존에 spring에서 쓰던 방식을 못써서 pageCount를 다시 적용
  const pageCount = Math.ceil(
    serverData.totalCount / serverData.pageRequestDTO.size,
  );

  const handlePageClick = (event: { selected: number }) => {
    // setcurrentPage(event.selected);

    const next = new URLSearchParams(searchParams);
    next.set('page', String(event.selected));
    setSearchParams(next);
  };

  if (error) return <Error />;
  // 검색
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    // 검색 시 첫 페이지가 뜨도록
    const next = new URLSearchParams(searchParams);
    next.set('page', '0');
    if (name === 'keyword') next.set('keyword', value);
    if (name === 'genre') next.set('genre', value ? value : '0');

    setSearchParams(next);
  };

  // if (loading) return <Loading />;
  if (error) return <Error />;
  return (
    <BasicLayout>
      <header className="mb-6 flex">
        <h1 className="grow text-[32px]">Book List</h1>
        <div>
          <input
            type="text"
            name="keyword"
            value={keyword}
            onChange={handleChange}
            placeholder="Search by title or author"
            className="w-50 rounded-sm border-2 border-gray-300 p-2 text-[.9em] leading-tight outline-0"
          />
          <select
            name="genre"
            className="ml-2 rounded-sm border-2 border-gray-300 p-2 text-[.9em] leading-tight outline-0"
            value={genre}
            onChange={handleChange}
          >
            <option value="">All Genres</option>
            {genres.map((genre, idx) => (
              <option key={idx} value={idx + 1}>
                {genre}
              </option>
            ))}
          </select>
        </div>
      </header>
      {loading ? (
        <Loading />
      ) : (
        <NovelList
          dtoList={serverData.dtoList}
          toggle={toggleAvailable}
          handlePageClick={handlePageClick}
          pageCount={pageCount}
          currentPage={currentPage}
        />
      )}
      {/* <NovelList /> */}
    </BasicLayout>
  );
};

export default Home;
