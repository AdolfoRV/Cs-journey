package model.observer

import scala.collection.mutable.ListBuffer

/**
 * Abstract class representing a subject in the Observer pattern.
 * 
 * @tparam T the type of the response that the subject will notify its observers with.
 */
trait Subject[T] {

  /** A list of observers subscribed to this subject. */
  private val observers: ListBuffer[Observer[T]] = ListBuffer()

  def addObserver(o: Observer[T]): Unit = observers += o

  def removeObserver(o: Observer[T]): Unit = observers -= o

  def notifyObservers(response: T): Unit = {
    for (o <- observers) {
      o.update(this, response)
    }
  }
}