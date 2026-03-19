# Cinema booking service
This training project was created as a study for Spring, Blaze Persistence and related technologies.
## Technologies
* servlet: ```Spring MVC```
* data: ```Spring Data JPA```, ```Blaze Persistence```
* user authentication: ```Spring Security```
* database: ```PostgreSQL```
* web: ```Thymeleaf```, ```Jquery```, ```Bootstrap```
## Functionality
* Movie info
  * description
  * show times for this movie by date and time
* Show time
  * displaying available tickets according to category of seat
  * possibility to book up to five tickets at a time
  * creating QR code to confirm booking
* Personal account
  * displaying available booking
  * displaying QR code to confirm booking
* Admin panel
  * movies and show times: add, edit, delete
  * set up movie to showing and withdrawal from theaters
  * persons: create and set up profession (director or/and actor)
* Secured access to the application
  * registration and authorization of customers
  * restricted access to the control panel
* In additional
  * displaying future show times by hall, date and time
  * movie theater info
  * scheme of halls
## Features
### Form validation
data validation is performing on the service side using package ```jakarta.validation```. Some custom annotations were implemented to validate 
	on field level and class level. Also regex was used.
### Exception handling
centralized exception class handling implemented in the app with annotation ```@ControllerAdvice```. In case of thymeleaf templates exception, class ```filters.ThymeleafExceptionsIntreceptor```, which implements ```jakarta.servlet.Filter```, is provided.

### Data base model
the application database consists of 18 tables which are mapped into 16 entities via Hibernate. Data access layer implemented via Spring Data JPA

### DTO
DTO layer is represent by Blaze-Persistence's entity views. Also entity views was used as form model.

### Utilites
```utilites.CinemaWorkingHours``` is creates time-line regardless of open and close cinema on same, or different day. For example, if cinema opening at 22.00 and 	closing at 03.00 time-line will look like: (22, 23, 00, 01, 02, 03). This class helps put show times in the schedule on right 	working day. For example, if show time start at 01.15 January 15 and cinema is open until 02.00, it will be added in the schedule 	on January 14.

```utilites.AvailableTimeForShowTime```
	convert list of show times to sorted by date and hall list of free times.

```utilites.ImageUploadService```
	download images to the file system. Creates QR codes to confirm booking.

## Screenshots
<img src="screenshot10.png" width="40%"> <img src="screenshot7.png" width="40%"> <img src="screenshot8.png" width="40%">
