import { useNavigate } from 'react-router-dom';
import NovelForm from '../../components/novels/NovelForm';
import BasicLayout from '../../layouts/BasicLayout';
import { initialNovel, type Novel } from '../../types/book';
import { postNovel } from '../../apis/novelApis';
import useLogin from '../../hooks/useLogin';

const AddNovel = () => {
  const navigate = useNavigate();
  const { isLogin } = useLogin();
  const handleCancel = () => {
    // 이전 페이지로 이동
    history.back();
  };
  const handleSubmit = async (formData: Novel) => {
    //업데이트 서버 요청
    try {
      const id = await postNovel(formData);
      // 상세보기
      navigate(`../${id}`);
      console.log('수정 후 {}', id);
    } catch (error) {
      console.log(error);
    }
  };
  //로그인 여부 확인?
  // 로그인 페이지 이동
  if (!isLogin) navigate('/member/login');

  return (
    <BasicLayout>
      <h1 className="text-[32px]">Add New Book</h1>
      <NovelForm
        novel={initialNovel}
        onSubmit={handleSubmit}
        onCancel={handleCancel}
      />
    </BasicLayout>
  );
};

export default AddNovel;
