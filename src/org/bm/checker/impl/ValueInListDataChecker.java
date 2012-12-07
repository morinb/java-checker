package org.bm.checker.impl;

import java.util.Arrays;
import java.util.Collection;

import org.bm.checker.CheckError;
import org.bm.checker.DataChecker;

public class ValueInListDataChecker implements DataChecker<String> {
   private final Collection<String> listValues;

   private final boolean ignoreCase;

   public ValueInListDataChecker(boolean ignoreCase, String... values) {
      this.listValues = Arrays.asList(values);
      this.ignoreCase = ignoreCase;
   }

   @Override
   public CheckError check(String object) {
      for (String value : listValues) {
         if (ignoreCase) {
            if (value.equalsIgnoreCase(object)) {
               return null;
            }
         } else {
            if (value.equalsIgnoreCase(object)) {
               return null;
            }
         }
      }

      return new CheckError("Value " + object + " has not been found in list.");
   }
}
