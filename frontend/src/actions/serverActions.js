'use server'

import axios from "axios";
import {accountsBackendUrl, expensesBackendUrl} from "../../config/configs";
import {redirect} from "next/navigation";
import {z} from 'zod';

export async function deleteExpenseRecord(id) {
    const response = await axios({
        method: "DELETE",
        url: expensesBackendUrl + `/api/expenses/delete/${id}`
    });
    return response.data;
}

export async function createExpense(prevState, formData) {

    // Creating a form validation schema with ZOD
    const formSchema = z.object({
        amount: z.coerce
            .number({
                required_error: 'Amount is required',
                invalid_type_error: 'Invalid type for amount'
            })
            .min(0.01, {message: 'Expense must be at least 1 cent'}),
        spentAt: z.coerce.date({
            required_error: 'Date is required',
            invalid_type_error: 'That\'s not a date'
        })
            .min(new Date('2000-01-01'), {message: 'No dates before 2000-01-01'})
            .max(new Date(), {message: 'No expenses from future, Doc'})
    });

    // Validating amount and spentAt fields
    const validatedFields = formSchema.safeParse({
        amount: formData.get('amount'),
        spentAt: formData.get('spentAt')
    });

    // If not successful return error messages
    if (!validatedFields.success) {
        return validatedFields.error.flatten().fieldErrors;
    }

    // making POST request to backend to add data
    await axios({
        method: 'post',
        url: expensesBackendUrl + '/api/expenses/create',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            amount: formData.get('amount'),
            spentAt: formData.get('spentAt'),
            comment: formData.get('comment'),
            category: formData.get('category'),
            account: formData.get('account')
        }
    });

    // redirecting user to tracker home page
    redirect("/tracker");
}

export async function updateAccountInfo(prevState, formData) {

    const formSchema = z.object({
        name: z.coerce
            .string({
                required_error: 'Name is required'
            })
            .min(1, {message: 'Length must be at least 1 symbol'}),
        balance: z.coerce
            .number({
                required_error: 'Balance is required'
            })
    });

    const validatedFields = formSchema.safeParse({
        name: formData.get('name'),
        balance: formData.get('balance')
    });

    if (!validatedFields.success) {
        return validatedFields.error.flatten().fieldErrors;
    }

    console.log(formData);

    await axios({
        method: 'PUT',
        url: accountsBackendUrl + '/api/accounts/update',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            id: formData.get('id'),
            name: formData.get('name'),
            balance: formData.get('balance')
        }
    });

    redirect(`/account/${formData.get('id')}`)

}