import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postRegister } from '../../apis/userApis';

const initState = { email: '', password: '', nickname: '' };

function RegisterForm() {
  const navigate = useNavigate();
  const [registerParam, setRegisterParam] = useState(initState);

  const handleSubmit = async (e: React.SubmitEvent) => {
    e.preventDefault();
    try {
      await postRegister(registerParam);
      alert('회원가입 성공');
      navigate('../login');
    } catch (error) {
      alert('회원가입 실패. 입력 정보를 확인해 주세요.');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setRegisterParam({ ...registerParam, [name]: value });
  };
  return (
    <form className="mt-6 flex flex-col gap-3" onSubmit={handleSubmit}>
      <input
        name="email"
        placeholder="id"
        required
        className="rounded-xs border-2 border-stone-300 p-2"
        onChange={handleChange}
      />
      <input
        type="password"
        name="password"
        placeholder="password"
        required
        className="rounded-xs border-2 border-stone-300 p-2"
        onChange={handleChange}
      />
      <input
        type="text"
        name="nickname"
        placeholder="nickname"
        required
        className="rounded-xs border-2 border-stone-300 p-2"
        onChange={handleChange}
      />
      <div className="p-2 text-center">
        <button
          type="submit"
          className="mx-1 my-6 rounded-[3px] bg-red-700 px-4.5 py-3 text-[1.2em] text-white hover:bg-red-900"
        >
          회원가입
        </button>
      </div>
    </form>
  );
}

export default RegisterForm;
