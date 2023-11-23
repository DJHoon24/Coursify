# Coursify

## Goal
Create an efficient digital solution (desktop app) to allow students to take notes and track them on a per-course basis integrated with general course information such as course ratings, assignment grades, and general comments.

## Team Members
DongHoon Jeong - dh2jeong1@uwaterloo.ca\
Imaan Gill - i29gill@uwaterloo.ca\
Paul Oh - pg2oh@uwaterloo.ca\
Sky Ouyang - s3ouyang@uwaterloo.ca

## Screenshots/Videos
Optional, but often helpful to have a screenshot or demo-video for new users.

## Quick-Start Instructions
- Open the repository locally

### To Build a packaged version of the app:
- Open and run Gradle coursify-project -> Tasks -> build -> build
- Open Gradle coursify-project -> app -> Tasks -> compose desktop -> package
- DMG(Mac) or MSI(Windows) version of Coursify should be added to the app/build/compose/binaries directory
- Run the DMG or MSI to create an executable version of Coursify.

### To run pre-packaged app:
- Go to releases folder and choose between local server and cloud server version of packaged app
    - Local server version will require local server to be running (Mentioned below)
    - Cloud server version will require the microservice to be running on Heroku
- DMG(Mac) or MSI(Windows) run the respective executable and it will isntall the app.

### Launch Local Server:
- Open and run Gradle coursify-project -> Tasks -> build -> build
- Open and run Gradle coursify-project -> KtorJwtAuth -> Tasks -> application -> run

## Project Documents
[Project Proposal](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Project-Proposal)

## Software Releases
[Software Release Notes](https://git.uwaterloo.ca/i29gill/cs346/-/wikis/Release-Notes)

```
cd existing_repo
git remote add origin https://git.uwaterloo.ca/i29gill/cs346.git
git branch -M main
git push -uf origin main
```
