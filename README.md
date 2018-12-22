# Android-Project
Local Event App

# Introduction
This project is to demonstrate an open project by Myriad Mobile for Internship Challenge.


# Login Screen

The project instruction has a clear instruction; however, some problems are required to be solved by project workers.

Initially, I had to figure out what internal procedures are to obtain a login token.

I was able to retrieve following response from "https://challenge.myriadapps.com/api/v1/login"

![Alt text](https://github.com/heecheon92/Android-Project/blob/master/login_postman.png "Login Postman")

Now I know that "Username" and "Password" parameters are required to receive a proper API response.

After putting arbitrary inputs for required parameters, I am getting a login token.

![Alt text](https://github.com/heecheon92/Android-Project/blob/master/token_postman.png "Login Token")

![Alt text](https://github.com/heecheon92/Android-Project/blob/master/login_screen.png "Login Screen")


# Event Screen

When attempting to access the event list, I got "message: This action is unauthorized."

So I added a "Authorization" header with a value of my login token.

I spent a fair amount of time to figure out which header is required. I know O-Auth uses "Authentication" as a default header.

I tried "Authentication" but no success. But the message says "unauthorized", so I tried with "Authorization".

![Alt text](https://github.com/heecheon92/Android-Project/blob/master/event_postman.png "Event Postman")
