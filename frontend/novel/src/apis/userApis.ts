import axios from 'axios';

import type { LoginForm } from '../types/user';

export const API_SERVER_HOST = '/api/member';

// 로그인 post 처리
export const postLogin = async (loginParam: LoginForm) => {
  const params = new URLSearchParams();
  // login 시 id는 username, 비밀번호는 password
  params.append('username', loginParam.email);
  params.append('password', loginParam.pw);

  const res = await axios.post(`${API_SERVER_HOST}/login`, params);
  console.log('서버 도착', res);
  return res.data;
};
