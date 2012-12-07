package org.bm.checker.impl;

import org.bm.checker.CheckError;
import org.bm.checker.DataChecker;

public class IsNotNullAndNotEmptyDataChecker implements DataChecker<String> {

   @Override
   public CheckError check(String object) {
      if (object == null || object.isEmpty()) {
         return new CheckError("data is null or empty");
      }
      return null;
   }

}
