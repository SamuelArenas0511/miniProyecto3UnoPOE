package org.example.eiscuno.model.exception;

public class emptyDeckException extends Exception {

        /**
         * Default constructor for the {@code GameException}.
         * @version 1.0
         */
        public emptyDeckException () {
            super();
        }

        /**
         * Constructor with a custom error message for the {@code GameException}.
         * @param message the custom error message.
         * @version 1.0
         */
        public emptyDeckException(String message) {
            super(message);
        }

        /**
         * Constructor with a custom error message and a cause for the {@code GameException}.
         * @param message the custom error message.
         * @param cause the cause of the exception.
         * @version 1.0
         */
        public emptyDeckException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructor with a cause for the {@code GameException}.
         * @param cause the cause of the exception.
         * @version 1.0
         */
        public emptyDeckException(Throwable cause) {
            super(cause);
        }

        /**
         * Exception for handling actions performed out of the board bounds.
         * Extends {@code IndexOutOfBoundsException}.
         * @version 1.0
         */
}
