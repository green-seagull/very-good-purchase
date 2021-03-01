import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Button, MenuItem, Select, Card } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';

class PurchaseForm extends React.Component {
    constructor(props) {
        super(props);

        const production = process.env.NODE_ENV === 'production';
        const randomTitle = Math.random().toString(36).substring(7);
        const randomAmount = Math.floor(Math.random() * 150);

        const amount = production ? "" : randomAmount;
        const title = production ? "" : randomTitle;

        this.state = {
            date: new Date().toISOString().slice(0, 10),
            amountDollars: amount,
            title: title,
            purchaseType: 'ps4',
            //Optional tags: [rebuy, expansion, bored, sale, planned]
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    render() {
        return (
            <Card id="purchaseForm">
                <form onSubmit={this.handleSubmit}>
                    <Grid container direction="column" justify="center" spacing={5}>
                        <Grid item>
                            <TextField id="date" label="date" name="date" autoFocus value={this.state.date} onChange={this.handleChange} ></TextField>
                        </Grid>
                        <Grid item>
                            <TextField id="title" label="Title" name="title" value={this.state.title} onChange={this.handleChange}></TextField>
                        </Grid>
                        <Grid item>
                            <TextField id="amountDollars" label="Amount" name="amountDollars" value={this.state.amountDollars} onChange={this.handleChange}></TextField>
                        </Grid>
                        <Grid item>
                            <Select label="Type" name="purchaseType" value={this.state.purchaseType} onChange={this.handleChange}>
                                <MenuItem value="book">book</MenuItem>
                                <MenuItem value="ps4">ps4</MenuItem>
                                <MenuItem value="switch">switch</MenuItem>
                                <MenuItem value="steam">steam</MenuItem>
                                <MenuItem value="PC">PC</MenuItem>
                                <MenuItem value="tool">tool</MenuItem>
                                <MenuItem value="collection">collection</MenuItem>
                            </Select>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit">Save</Button>
                        </Grid>
                    </Grid>
                </form>
            </Card>
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
        event.preventDefault();

        const json = JSON.stringify(this.state);

        fetch(`${apiDomain()}/api/purchases`, {
            method: 'PUT',
            body: json,
            headers: { 'Content-Type': 'application/json' },
        }).then(function (response) {
            console.log(response);
        }).catch(function(error) {
            console.log(error);
            alert('Failed to submit: ' + error);
        });
    }
}

export function apiDomain() {
    const production = process.env.NODE_ENV === 'production'
    return production ? "" : "http://localhost:8080";
}
export default PurchaseForm