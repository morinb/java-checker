package org.bm.checker;

public class CheckError<T> {
   private final T message;

   public CheckError(T message) {
      this.message = message;
   }

   public T getMessage() {
      return this.message;
   }
}
