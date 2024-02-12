export default function ExpensesTable({data, onRecordDelete}) {

    const formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    })

    return (
        <table>
            <thead>
                <tr>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Comment</th>
                    <th>Category</th>
                    <th>Account</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {
                    data.map((datum) => {
                        return [
                            <tr key={datum.id}>
                                <td>{formatter.format(datum.amount)}</td>
                                <td>{datum.spentAt}</td>
                                <td>{datum.comment}</td>
                                <td>{datum.category.category}</td>
                                <td>{datum.account.name}</td>
                                <td>
                                    <button onClick={() => onRecordDelete(datum.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ];
                    })
                }
            </tbody>
        </table>
    )
        ;
}
