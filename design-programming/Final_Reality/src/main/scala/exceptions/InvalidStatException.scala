package exceptions

/** Exception indicating that an invalid stat value was encountered.
 *
 *  This exception is thrown when a character or entity has a stat with an
 *  invalid or unexpected value, which may disrupt gameplay or calculations.
 *
 *  @throws InvalidStatException when an invalid stat value is found.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class InvalidStatException extends Exception(s"An invalid stat was found")