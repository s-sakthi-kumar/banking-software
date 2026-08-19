import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { getAccount } from "../api";
import {
    setAccount,
    setLoading,
    setError
} from "../redux/accountSlice";

function AccountDetails({ accountId }) {
    const dispatch = useDispatch();

    const { account, loading, error } = useSelector(
        (state) => state.account
    );

    useEffect(() => {
        async function fetchAccount() {
            dispatch(setLoading(true));

            try {
                const data = await getAccount(accountId);

                dispatch(setAccount(data));
            } catch (err) {
                dispatch(setError(err.message));
            } finally {
                dispatch(setLoading(false));
            }
        }

        fetchAccount();
    }, [accountId, dispatch]);

    if (loading) {
        return <p>Loading account...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    if (!account) {
        return <p>No account found.</p>;
    }

    return (
        <div>
            <h2>Account Details</h2>

            <p>
                Name: {account.firstName} {account.lastName}
            </p>

            <p>Email: {account.email}</p>

            <p>Phone: {account.phone}</p>
        </div>
    );
}

export default AccountDetails;
