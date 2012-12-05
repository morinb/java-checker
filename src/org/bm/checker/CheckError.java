package org.bm.checker;

public class CheckError {
   private final String message;

   public CheckError(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }
}
