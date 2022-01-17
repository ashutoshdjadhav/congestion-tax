# Congestion Tax Calculator

## Congestion tax implemented using Java8, Spring Boot.(Please check java folder for spring boot project)

## Data are externalized. You can changes parameter in below files and run application. Please check below files:
congestion-tax/src/main/resources/VehicleExempt.json
congestion-tax/src/main/resources/TaxRates.json
congestion-tax/src/main/resources/Holidays.json
congestion-tax/src/main/resources/application.properties

## Below is simple request details
URL: http://localhost:8080/tax/calculate
Request Body:
{
    "vehicleType" : "car",
    "tollDateTimeList" : [
        "2013-01-14T21:00:00",
        "2013-01-15T21:00:00",
        "2013-02-07T06:23:27",
        "2013-02-07T15:27:00",
        "2013-02-08T06:27:00",
        "2013-02-08T06:20:27",
        "2013-02-08T14:35:00",
        "2013-02-08T15:29:00",
        "2013-02-08T15:47:00",
        "2013-02-08T16:01:00",
        "2013-02-08T16:48:00",
        "2013-02-08T17:49:00",
        "2013-02-08T18:29:00",
        "2013-02-08T18:35:00",
        "2013-03-26T14:25:00",
        "2013-03-28T14:07:27"
    ]
}


##Make sure you add valid date time format in request body.

