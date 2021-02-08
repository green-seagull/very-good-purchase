import React from 'react';
import ReactDOM from 'react-dom';

class PurchaseForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            date: '2020-12-27',
            amountDollars: 30,
            title: '',
            purchaseType: 'ps4',
            //Optional tags: [rebuy, expansion, bored, sale, planned]
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div>
                    <label>Name: <input type="text" name="date" autoFocus value={this.state.date} onChange={this.handleChange} /></label>
                    <label>Title: <input type="text" name="title" autoFocus value={this.state.title} onChange={this.handleChange} /></label>
                    <label>Amount: <input type="text" name="amountDollars" value={this.state.amountDollars} onChange={this.handleChange} /></label>
                    <label>Type:
                        <select name="purchaseType" value={this.state.value} onChange={this.handleChange}>
                            <option value="book">book</option>
                            <option value="ps4">ps4</option>
                            <option value="switch">switch</option>
                            <option value="steam">steam</option>
                            <option value="PC">PC</option>
                            <option value="tool">tool</option>
                        </select>
                    </label>
                </div>
                <input type="submit" value="Submit" />
            </form>
        );
    }

    handleChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        const json = JSON.stringify(this.state);

        fetch(`${apiDomain()}/api/purchases`, {
            method: 'PUT',
            body: json,
            headers: { 'Content-Type': 'application/json' },
        }).then(function(response) {
            console.log(response);
        });
    }
}

export function apiDomain() {
    const production = process.env.NODE_ENV === 'production'
    return production ? "" : "http://localhost:8080";
}

ReactDOM.render(
    <div>
        <PurchaseForm />
    </div>,
    document.getElementById('root')
);
