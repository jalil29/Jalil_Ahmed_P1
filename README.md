# Expense Reimbursement API

## Project Description

The Employee Reimbursement System (ERS) is REST API that helps manage the process of reimbursing employees for expenses. Employees can be created and edited via the API. Expenses for employees can be added and updated to pending and approved. Reviewed expenses can not be edited.

## Technologies Used
* Java
* Maven
* Lombok
* PostgreSQL

## Features
* Submit simple expenses reports to later be approved or declined.
* Accessible via Amazons Elastic Beanstalk service.
* Returns data as in JSON format.

To-do list:
* Expand on data stored on the server.
* Implement a front-end UI with proper CSS and HTML formatting
* Modify Server to pseudo-delete employees to keep proper records

## Getting Started
   
> Dependencies are Managed via Maven's management.
> Set a System Variable _ExpensesDatabase_ with the associated database URL in the following form jdbc:subprotocol:subname
> > For Examplle, if running on LocalHost it will be expected jdbc:http://localhost:5000/
