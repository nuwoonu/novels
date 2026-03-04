// 로그인 정보를 쿠키에 저장하거나 제거하는 함수 정의

import { Cookies } from 'react-cookie';

const cookies = new Cookies();

export const setCookie = (name: string, value: string, days: number) => {
  const expires = new Date();
  expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
  return cookies.set(name, value, { path: '/', expires });
};
export const getCookie = (name: string) => {
  return cookies.get(name);
};
export const removeCookie = (name: string, path = '/') => {
  cookies.remove(name, { path });
};
