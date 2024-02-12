import axios from "axios";
import {accountsBackendUrl, expensesBackendUrl} from "../../config/configs";

export async function fetchCategories() {
    const response = await axios(
        {
            method: 'get',
            url: expensesBackendUrl + '/api/categories'
        }
    );
    return response.data;
}

export async function fetchAccounts() {
    const response = await axios(
        {
            method: 'get',
            url: accountsBackendUrl + '/api/accounts'
        }
    );
    return response.data;
}

export async function fetchAccount(accountId) {
    try {
        const response = await axios(
            {
                method: 'get',
                url: accountsBackendUrl + '/api/accounts/' + accountId
            }
        );
        return response.data
    } catch (e) {
        console.log(e);
        return {};
    }
}

export async function fetchExpenses() {
    try {
        const response = await axios(
            {
                method: 'get',
                url: expensesBackendUrl + '/api/expenses'
            }
        );
        return response.data;
    } catch (e) {
        console.log(e);
        return [];
    }
}

export async function fetchExpensesForAccount(accountId) {
    try {
        const response = await axios(
            {
                method: 'get',
                url: expensesBackendUrl + '/api/expenses/account/' + accountId
            }
        );
        return response.data;
    } catch (e) {
        console.log(e);
        return [];
    }
}