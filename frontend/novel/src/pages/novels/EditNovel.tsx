import { useNavigate, useParams } from 'react-router-dom';
import Error from '../../components/common/Error';
import NovelForm from '../../components/novels/NovelForm';
import BasicLayout from '../../layouts/BasicLayout';
import { useNovel } from '../../hooks/useNovel';
import Loading from '../../components/common/Loading';
import type { Novel } from '../../types/book';
import { putNovel } from '../../apis/novelApis';
import useLogin from '../../hooks/useLogin';

const EditNovel = () => {
  //http://localhost:5173/novels/edit/119
  // id 가져오기
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate(); // 네비게이트 호출
  const { isLogin } = useLogin();
  // 서버로 novel 요청
  const { serverData, loading, error } = useNovel(id);
  const handleCancel = (id: number) => {
    //이전페이지 이동
    navigate(`../${id}`);
  };
  const handleSubmit = async (formData: Novel) => {
    //업데이트 서버 요청
    try {
      const result = await putNovel(formData);
      // 상세보기
      navigate(`../${id}`);
      console.log('수정 후 {}', result);
    } catch (error) {
      console.log(error);
    }
  };

  //로그인 여부 확인?
  // 로그인 페이지 이동
  if (!isLogin) navigate('/member/login');
  if (error) return <Error />;
  return (
    <BasicLayout>
      <h1 className="text-[32px]">Edit Book</h1>
      {loading ? (
        <Loading />
      ) : (
        <NovelForm
          novel={serverData}
          onCancel={handleCancel}
          onSubmit={handleSubmit}
        />
      )}
    </BasicLayout>
  );
};

export default EditNovel;
