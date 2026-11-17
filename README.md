# Queue Ride
## Virginia Tech | CS2114 | Project 3 | Group 29
### Members

- Jacob Fisher ([@fisherjacobc](https://github.com/fisherjacobc))
- Aishwarya Shah ([@aishwaryashah28](https://github.com/aishwaryashah28))

## Overview
Queue Ride is an interactive, console-based queue system written in Java that simulates a car rideshare service, similar to Uber. Users can be prompted to enter their name and destination to request a ride. Once they enter the car, the first person who entered will be the first one dropped off. The second person is be dropped off next, and so on. Users can also be able to see who is in the car and who will be dropped off next.

## How to Compile & Run
Open up VS Code with the Java Language Support Extension Pack, and head to [`Main.java`](/src/rideshare/Main.java). From there, you can click on 'Run' and go through the simulation.

VS Code will automatically compile for you, but if you wish to compile manually, you can use `javac **/*.java`.

To run tests, head to the Testing tab (looks like a beaker icon), and you can run the full suite of tests all at once and get a code coverage report.

## Reflection

### What changed from the spec
- **`Node` and `Queue` are generic** so the same queue works for both the waiting line and the cars, and could be tested before `Passenger` existed
- **`Queue` accepts `null`**, so null checks moved up into `Car` and `RideshareModel`, which know what a valid entry is
- **Empty queues and cars return `null`** instead of throwing, since the spec contradicted itself and `null` is simpler for the controller to handle
- **A full car isn't an exception**: the waiting line has no limit, and seat checks happen when assigning passengers to cars
- **`Car` has no stored destination**: it reads the destination of its front passenger so it never goes out of date instead of trying to update the same value in two places
- **`initializeCars` won't replace occupied cars**, so passengers can't be silently deleted
- **Added `getElapsedMinutes()`** so the controller can display the clock
- **Menu input is validated in the view and controller together**, and a quit option was added

### Did the model stand alone?
- The model classes manage state and logic without printing or reading user input.
- Model tests call the model directly.
- Controller tests use a test view that supplies input and records output.

### What happened to the unknowns
- **Queue behavior after emptying:** figured out and tested
- **Testing the last drop-off:** tested for `Car`, still needs model-level tests
- **What triggers drop-offs:** for now the user drops off passengers manually, and clock-driven drop-offs are planned
- **Assigning passengers to cars:** passengers fill the lowest-numbered car with a free seat
- **Estimating arrival times:** each stop takes a random time between a set minimum and maximum, and the estimate is shown as that range

### What doesn't work yet
- No model tests, and no tests for the controller/view
- The roadmap view is only a placeholder
- Simulating time only advances the clock
- Dropped-off passengers aren't recorded

## Next Steps

### Finishing the Final Build
1. Run and review the model and controller tests, then fix any failures. *(Owner: Aishwarya)*
2. Write the Roadmap View *(Owner: Jacob)*
3. Walk through the full program together, including bad inputs *(Owner: Jacob and Aishwarya)*

### Change feature: drop-offs driven by the clock
- **What it is:** each stop takes a random amount of time between a set minimum and maximum (X to Y), and advancing the clock drops off passengers as stops are reached
- **Why it's not trivial:** each car needs its own countdown, one time step can cause many drop-offs, and the results need to reach the view
- **What it touches:** `Car`, `RideshareModel.updateTravelTime`, the controller's time simulation, and a new view method for showing drop-offs
- **What shouldn't change:** `Queue`, `Node`, `Passenger`, and manual drop-offs
- *(Owner: Aishwarya for the model; Jacob for the controller and view)*

### Stretch goals
- **Committed: multiple cars**, which already works but needs tests *(Owner: Aishwarya)*
- **Committed: time simulation**, as the change feature above
- **Committed: simple roadmap view** showing each car's upcoming stops *(Owner: Jacob and Aishwarya)*
- **Committed: arrival-time estimates**, shown as a range: a passenger *n* stops away arrives in a random amount of time, with the actual time picked at random within each stop's range

### New tests needed
- **`RideshareModelTest`** for the spec's test plan plus the changes above *(Owner: Aishwarya)*
- **`CarTest`** for clock-driven drop-offs *(Owner: Aishwarya)*
- Look into tests for the controller and/or view classes *(Owner: Jacob and Aishwarya)*

### Three-Minute Demo

**0:00–0:20 — Introduce the project**

Open `src/rideshare/Main.java` and click Run. Press Enter whenever the program pauses after displaying a message.

**0:20–0:40 — Create the cars**

- Enter `1` to initialize cars.
- Enter `2` for the number of cars.
- Enter `1` for the capacity of each car.

**0:40–1:10 — Add three passengers**

- Enter `2`, then enter `Maya` and `Library`.
- Enter `2` again, then enter `Sam` and `Gym`.
- Enter `2` again, then enter `Alex` and `Dining Hall`.
- Enter `5` to display the passengers.

**1:10–1:40 — Assign passengers and show a full system**

- Enter `3` to assign Maya to car 1.
- Enter `3` again to assign Sam to car 2.
- Enter `3` again to try assigning Alex.
- Enter `5` to show the passengers.

**1:40–2:05 — Drop off a passenger and reuse the seat**

- Enter `4`, then enter `1` for the car ID.
- Enter `3` to assign Alex to the available seat.
- Enter `5` to display the updated passengers.

**2:05–2:30 — Demonstrate invalid input**

- Enter `4`, then enter `99` for a car that does not exist.
- Enter `2`, enter only spaces for the passenger name, and enter `Library` as the destination.

**2:30–2:50 — Show the tests**

Switch to the VS Code Testing panel and run `RideshareModelTest` and `RideshareControllerTest`.

Show the actual test results.

**2:50–3:00 — Finish**

Return to the program and enter `0` to quit.

## Attribution + GenAI Note
### Attribution
#### Deliverable 3
| File | Attribution |
| ---- | ----------- |
| rideshare.Main | Jacob (Wrote/Reviewed) |
| rideshare.RideshareController | Jacob (Wrote/Reviewed) |
| rideshare.RideshareModel | Aishwarya/GenAI (Wrote), Aishwarya (Reviewed) |
| rideshare.RideshareView | Jacob (Wrote/Reviewed) |
| util.Car | Aishwarya (Wrote/Reviewed) |
| util.CarTest | Jacob (Wrote/Tested) |
| util.Node | Jacob (Wrote/Reviewed) |
| util.NodeTest | Jacob (Wrote/Reviewed) |
| util.Passenger | Aishwarya (Wrote/Reviewed) |
| util.PassengerTest | Jacob (Wrote/Tested) |
| util.Queue | Jacob (Wrote/Reviewed) |
| util.QueueTest | Jacob (Wrote/Reviewed) |

#### Deliverable 4
| File | Attribution |
| ---- | ----------- |
| rideshare.RideshareModel | Aishwarya added the dropped-off passenger history and a method for retrieving it. |
| rideshare.RideshareModelTest | Aishwarya added tests for model operations, invalid input, and dropped-off passenger history. |
| rideshare.RideshareControllerTest | Aishwarya added controller tests and a test view that supplies input and records messages. |
| README.md | Jacob updated the main README content. Aishwarya drafted Deliverable 4 attribution. |

### GenAI
#### Deliverable 3
GenAI was used in part for the initial programming of the `RideshareModel` class. The output was modified and thoroughly reviewed by Aishwarya.
#### Deliverable 4
Aishwarya used GenAI to help write the `RideshareModelTest` and `RideshareControllerTest` classes, including tests for normal behavior and invalid input. Before using the code, Aishwarya thoroughly reviewed it and made changes as needed to make sure it matched the project.

