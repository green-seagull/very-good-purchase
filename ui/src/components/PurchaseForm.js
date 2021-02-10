import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Button, MenuItem, Select } from '@material-ui/core';

class PurchaseForm extends React.Component {
    constructor(props) {
        super(props);

        const randomTitle = Math.random().toString(36).substring(7);
        const randomAmount = Math.floor(Math.random() * 150);

        this.state = {
            date: new Date().toISOString().slice(0,10),
            amountDollars: randomAmount,
            title: randomTitle,
            purchaseType: 'book',
            //Optional tags: [rebuy, expansion, bored, sale, planned]
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div>
                    <TextField id="date" label="date" name="date" autoFocus value={this.state.date} onChange={this.handleChange} ></TextField>
                    <TextField id="title" label="Title" name="title" value={this.state.title} onChange={this.handleChange}></TextField>                    
                    <TextField id="amountDollars" label="Amount" name="amountDollars" value={this.state.amountDollars} onChange={this.handleChange}></TextField>
                    {/* <InputLabel>Type</InputLabel> */}
                    <Select label="Type" name="purchaseType" value={this.state.purchaseType} onChange={this.handleChange}>
                        <MenuItem value="book">book</MenuItem>
                        <MenuItem value="ps4">ps4</MenuItem>
                        <MenuItem value="switch">switch</MenuItem>
                        <MenuItem value="steam">steam</MenuItem>
                        <MenuItem value="PC">PC</MenuItem>
                        <MenuItem value="tool">tool</MenuItem>
                    </Select>
                </div>
                <Button variant="contained" color="primary" type="submit">Save</Button>
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
export default PurchaseForm