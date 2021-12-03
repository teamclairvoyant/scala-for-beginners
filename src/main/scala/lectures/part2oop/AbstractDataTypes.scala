package lectures.part2oop

object AbstractDataTypes extends App {

  /**
   * There are certain situations when you want to leave some fields and methods unimplemented. These are called abstract members.
   * Classes that contain abstract fields or methods are known as Abstract Classes
   */
  abstract class Animal {
    val creatureType: String

    def eat(): Unit

    def walk(): Unit = println("I am walking")
  }

  /**
   * Abstract classes cannot be instantiated
   */

  // Compilation Error:  Animal is abstract; it cannot be instantiated
  // val animal = new Animal

  /**
   * Abstract classes are meant to be extended later
   * The class that extends the abstract class need to implement the abstract fields and methods
   */
  class Dog extends Animal {
    val creatureType: String = "Canine"

    def eat(): Unit = println("Crunch Crunch")
  }


  /**
   * An abstract class can also contain a constructor
   * Constructor of an abstract class is called when the instance of an inherited class is created.
   */
  abstract class Author(name: String, topic: String) {
    def details(): Unit
  }

  class MyAuthor(name: String, topic: String) extends Author(name, topic) {
    def details(): Unit = println(s"Author name: $name, Topic name: $topic")
  }

  var obj = new MyAuthor("Tom", "Abstract Class")
  obj.details() // Author name: Tom, Topic name: Abstract Class

  /**
   * Traits - alternate abstract data type in scala
   * Like abstract classes, traits also contains abstract fields and methods
   * Unlike abstract classes, traits can be inherited along classes
   */
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Lion extends Carnivore {
    def eat(animal: Animal): Unit = println(s"I am a Lion and I am eating ${animal.creatureType}")
  }

  /**
   * class Crocodile inherits members from both Animal and Carnivore
   */
  class Crocodile extends Animal with Carnivore {
    val creatureType: String = "croc"

    def eat(): Unit = println("nomnomnom")

    def eat(animal: Animal): Unit = println(s"I am a croc and I am eating ${animal.creatureType}")
  }

  val dog = new Dog
  val crocodile = new Crocodile
  crocodile.eat(dog) // I am a croc and I am eating Canine


  /**
   * A class can extend as many traits as possible
   */
  trait ColdBlooded
  // class A extends Animal with Carnivore with ColdBlooded


  /**
   * Both abstract classes and traits can contain both abstract and non abstract members
   * Traits do not have constructor parameters
   * Traits = Behaviour, Abstract Class = Thing
   */


  /**
   * Rather than adding a Scala trait to an entire class, you just want to add a trait to an object instance when the object is created.
   */
  trait Climb

  val cannotClimbDogInstance = new Dog

  val canClimbDogInstance = new Dog with Climb


  /**
   * Let’s consider another restriction where an employee(HR / TechLead) can have a trait CodingSkills only if an employee is a developer
   */
  trait Developer {
    def writeCode(): String = "I can code in Java"
  }

  trait Tester {
    def writeCode(): String = "I can code in Selenium"
  }

  /**
   * We need to ensure that CodingSkills can be extended by types if and only if they also extend the Developer type
   */
  trait CodingSkills {
    this: Developer =>
  }

  class TechLead extends Developer with CodingSkills

  // Compilation Error: illegal inheritance
  // class HR extends CodingSkills

  class BigDataArchitect extends Developer with Tester {
    /**
     * If we want to explicitly call a conflicting method in the parent traits, then the super keyword can be given a type
     */
    override def writeCode(): String =
      super[Developer].writeCode() + ", " + super[Tester].writeCode()
  }

  val bda = new BigDataArchitect
  println(bda.writeCode())


  /**
   * Sealed Traits
   */
  sealed trait X

  class A extends X

  class B extends X

  class C extends X
}
