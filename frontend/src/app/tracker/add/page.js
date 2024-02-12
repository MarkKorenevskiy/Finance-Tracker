'use client'
import Link from "next/link";
import {useEffect, useState} from "react";
import {createExpense} from "@/actions/serverActions";
import {fetchAccounts, fetchCategories} from "@/actions/clientActions";
import {useFormState} from 'react-dom';

// Creating initial state for validation schema
const initialState = {
    amount: '',
    spentAt: ''
}

export default function TrackerAddExpense() {

    // Init useFormState hook
    const [state, formAction] = useFormState(createExpense, initialState);

    // Saving data that we're going to use to add new record
    const [categories, setCategories] = useState([]);
    const [accounts, setAccounts] = useState([]);

    // Getting accounts and categories for form
    useEffect(() => {
        fetchCategories().then(res => {
            setCategories(res);
        });

        fetchAccounts().then(res => {
            setAccounts(res);
        });

    }, []);

    return (
        <main>
            <h1>Add new expense</h1>
            <form action={formAction}>
                <label>Amount</label>
                <input type="text" name='amount'/>
                <span>{state?.amount}</span>
                <br/>
                <label>Date</label>
                <input type="date" name='spentAt'/>
                <span>{state?.spentAt}</span>
                <br/>
                <label>Comment</label>
                <input type="text" name='comment'/>
                <br/>
                <label>Category</label>
                <select name="category">
                    {
                        categories.map(category => {
                            return [
                                <option key={category.id} value={category.id}>
                                    {category.category}
                                </option>
                            ]
                        })
                    }
                </select>
                <br/>
                <label>Account</label>
                <select name="account">
                    {
                        accounts.map(account => {
                            return [
                                <option key={account.id} value={account.id}>
                                    {account.name}
                                </option>
                            ]
                        })
                    }
                </select>
                <button type={"submit"}>Submit</button>
                <button type={"reset"}>Reset</button>
            </form>
            <Link href={"/tracker"}>Back to all expenses</Link>
        </main>
    );
}