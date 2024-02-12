'use client';
import ExpensesTable from '@/components/expense/ExpensesTable';
import {useEffect, useState} from 'react';
import {deleteExpenseRecord} from "@/actions/serverActions";
import CategoryFilter from "@/components/expense/CategoryFilter";
import OrderBySection from "@/components/expense/OrderBySection";
import Accounts from "@/components/expense/Accounts";
import {fetchAccounts, fetchCategories, fetchExpenses} from "@/actions/clientActions";
import AddExpenseBtn from "@/components/expense/AddExpenseBtn";
import AccountFilter from "@/components/expense/AccountFilter";

export default function TrackerHome() {
    const [expenses, setExpenses] = useState([]);
    const [categories, setCategories] = useState([]);
    const [accounts, setAccounts] = useState([]);

    // Filter categories chosen by user
    const [chosenCategory, setChosenCategory] = useState('all');
    const [chosenAccount, setChosenAccount] = useState('all');

    // Determines in what order dates will be sorted. true - increase, false - decrease
    const [dateOrderFlag, setDateOrderFlag] = useState(false);

    // Determines in what order dates will be sorted. true - increase, false - decrease
    const [amountOrderFlag, setAmountOrderFlag] = useState(false);

    function handleOrderClick(orderParam) {
        let sortedExpenses = expenses;
        switch (orderParam) {
            case 'amount':
                sortedExpenses.sort((a, b) => {
                    if (amountOrderFlag) {
                        return parseFloat(a.amount) - parseFloat(b.amount);
                    } else {
                        return parseFloat(b.amount) - parseFloat(a.amount);
                    }
                });
                setAmountOrderFlag(!amountOrderFlag);
                break;
            case 'date':
                sortedExpenses.sort((a, b) => {
                    if (dateOrderFlag) {
                        console.log(dateOrderFlag)
                        return new Date(a.spentAt) - new Date(b.spentAt);
                    } else {
                        return new Date(b.spentAt) - new Date(a.spentAt);
                    }
                });
                setDateOrderFlag(!dateOrderFlag);
                break;
            default:
                break;
        }
        setExpenses([...sortedExpenses]);
    }

    function handleExpenseDelete(id) {
        deleteExpenseRecord(id).then((res) => {
            if (res) {
                // Removing deleted expense record from the list
                let modifiedExpenses = expenses;
                modifiedExpenses = modifiedExpenses.filter(e => e.id !== id);
                setExpenses([...modifiedExpenses]);

                // Updating account with new values from the backend
                fetchAccounts().then(res => setAccounts(res));
            }
        });
    }

    function handleCategoryChoice(categoryId) {
        setChosenCategory(categoryId);
    }

    function handleAccountChoice(accountId) {
        setChosenAccount(accountId);
    }

    function filterExpenses() {
        return expenses.filter(expense =>
            (chosenCategory === 'all' || expense.category.id === chosenCategory) &&
            (chosenAccount === 'all' || expense.account.id === chosenAccount)
        );
    }

    // fetching all expenses and categories on page loading
    useEffect(() => {
        fetchExpenses().then(res => setExpenses(res));
        fetchCategories().then(res => setCategories(res));
        fetchAccounts().then(res => setAccounts(res));
    }, []);

    return (
        <main>
            <h1>Tracker home page</h1>
            <Accounts accounts={accounts}/>
            <CategoryFilter categories={categories}
                            onCategoryChoice={handleCategoryChoice}/>
            <AccountFilter accounts={accounts}
                           onAccountChoice={handleAccountChoice}/>
            <OrderBySection onOrderClick={handleOrderClick}
                            dateFlag={dateOrderFlag} amountFlag={amountOrderFlag}/>
            <AddExpenseBtn/>
            <ExpensesTable data={filterExpenses()} category={chosenCategory}
                           onRecordDelete={handleExpenseDelete}/>
        </main>
    );
}
