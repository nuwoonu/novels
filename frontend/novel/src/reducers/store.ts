import { configureStore } from '@reduxjs/toolkit';
import { loginSlice } from './loginSlice';

// 애플리케이션 내에 공유되는 상태 데이터들을 관리할 목적으로 저장소를 쓴다.
export const store = configureStore({
  reducer: {
    auth: loginSlice.reducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
