import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    account: null,
    loading: false,
    error: null
};

const accountSlice = createSlice({
    name: "account",

    initialState,

    reducers: {
        setAccount(state, action) {
            state.account = action.payload;
        },

        setLoading(state, action) {
            state.loading = action.payload;
        },

        setError(state, action) {
            state.error = action.payload;
        }
    }
});

export const {
    setAccount,
    setLoading,
    setError
} = accountSlice.actions;

export default accountSlice.reducer;
