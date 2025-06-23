package model.observer

/** An Observer is subscribed to a Subject, which notifies them of any changes */
trait Observer[T] {

  /** Updates the observer of any changes by the Subject
   *
   * @param subject the one who notifies
   * @param value the change that's notified
   */
  def update(subject: Subject[T], value: T): Unit
}