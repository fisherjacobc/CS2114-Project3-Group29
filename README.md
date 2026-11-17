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
- Yes, the model classes never print or read input and don't depend on the view or controller
- All existing tests run without the view or controller

### What happened to the unknowns
- **Queue behavior after emptying:** figured out and tested
- **Testing the last drop-off:** tested for `Car`, still needs model-level tests
- **What triggers drop-offs:** for now the user drops off passengers manually, and clock-driven drop-offs are planned
- **Assigning passengers to cars:** passengers fill the lowest-numbered car with a free seat
- **Estimating arrival times:** each stop takes a random time between a set minimum and maximum, and the estimate is shown as that range

### What doesn't work yet
- The roadmap view is only a placeholder
   - Arrival-time estimates aren't shown yet.

### How did your design absorb the change?
- `Car`: each car's stop countdown and drop-offs triggered by time (model)
- `RideshareModel.updateTravelTime`: advances every car's countdown along with the clock (model)
- `RideshareController`: Menu choice 8 now reads a number of minutes, and handleTimeSimulation calls the model and works out who was dropped off
- `RideshareView`: new displayTimeSimulation method, and "(Inactive)" was removed from the menu

### Did the change cross the model/view line?
We touched only the four places we originally mentioned (`Car`, `updateTravelTime`, the controller's time simulation parts, and one new view method). `Queue`, `Node`, `Passenger` and manual drop-offs weren't affected.

### What changed from your D3 plan?
- Negative minutes are rejected by the model and shown as an error.
- Input that isn't a whole number makes the view ask again.
- Zero minutes is allowed and shows the clock without any drop-offs.
- We now handle the case where no cars exist, filled the remaining `Queue` test gaps, reject `null` in the controller, and handle end of input (Ctrl+D) without crashing.
- The console UI view was reworked to be much more appealing

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

### Stretch goals (ALL comitted)
- **Committed: multiple cars**, which already works but needs tests *(Owner: Aishwarya)*
- **Committed: time simulation**, as the change feature above
- **Committed: simple roadmap view** showing each car's upcoming stops *(Owner: Jacob and Aishwarya)*
- **Committed: arrival-time estimates**, shown as a range: a passenger *n* stops away arrives in a random amount of time, with the actual time picked at random within each stop's range

### New tests needed
- **`CarTest`** for clock-driven drop-offs *(Owner: Aishwarya)*
- Look into tests for the controller and/or view classes *(Owner: Jacob and Aishwarya)*

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
| rideshare.RideshareController | Jacob added/updated methods for handling time simulations and dropoffs. |
| rideshare.RideshareControllerTest | Aishwarya added controller tests and a test view that supplies input and records messages. |
| rideshare.RideshareView | Jacob revamped the view to look cleaner. |
| README.md | Jacob updated the main README content. Aishwarya drafted Deliverable 4 attribution. |

### GenAI
#### Deliverable 3
GenAI was used in part for the initial programming of the `RideshareModel` class. The output was modified and thoroughly reviewed by Aishwarya.
#### Deliverable 4
Aishwarya used GenAI to help write the `RideshareModelTest` and `RideshareControllerTest` classes, including tests for normal behavior and invalid input. Before using the code, Aishwarya thoroughly reviewed it and made changes as needed to make sure it matched the project.

