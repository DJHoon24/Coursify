# Coursify

## Goal
Create an efficient digital solution (desktop app) to allow students to take notes and track them on a per-course basis integrated with general course information such as course ratings, assignment grades, and general comments.

## Team Members
DongHoon Jeong - dh2jeong1@uwaterloo.ca\
Imaan Gill - i29gill@uwaterloo.ca\
Paul Oh - pg2oh@uwaterloo.ca\
Sky Ouyang - s3ouyang@uwaterloo.ca

## Help Section for App
Users can view a help section to describe user flows and how to use Coursify found after the user has logged into the application.
- Login or sign up as a User.
- Look to the top left of sidebar and click settings button.
- Go to help page of dialog.

## Quick-Start Instructions
Can be found at [Setup of Application](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Instructions-and-Setup-of-Application) or follow instructions below:

- Open the repository locally

### To Build a packaged version of the app:
- Open and run Gradle coursify-project -> Tasks -> build -> build
- Open Gradle coursify-project -> app -> Tasks -> compose desktop -> package
- DMG(Mac) or MSI(Windows) version of Coursify should be added to the app/build/compose/binaries directory
- Run the DMG or MSI to create an executable version of Coursify.

### To run pre-packaged app:
- Go to releases folder and choose between default cloud microservice packaged app or the backup local microservice packaged app (If cloud is down).
  - DEFAULT: Version requires the microservice to be running on Heroku (Default up and running)
    - If Cloud server is down or requires a restart for marking, reach out to Paul Oh (pg2oh@uwaterloo.ca).
  - BACKUP: Version requires local server to be running (Mentioned below)
- DMG(Mac) or MSI(Windows) run the respective executable to install the app.

### Launch Local Server:
- Open and run Gradle coursify-project -> Tasks -> build -> build
- Open and run Gradle coursify-project -> KtorJwtAuth -> Tasks -> application -> run

## Project Documents

### Project Proposal
[Project Proposal](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Project-Proposal)

### Main sections of Project Documentation
[Main Sections](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Main-sections)

### Meeting Minutes
[Meeting Minutes](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Meeting-Minutes)

### Software Releases
[Software Release Notes](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Release-Notes)

