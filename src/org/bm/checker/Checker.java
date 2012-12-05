package org.bm.checker;

import java.util.LinkedList;
import java.util.List;

public class Checker<T> {

   private DataGetter<T> getter;

   private final List<DataChecker<T>> checkers;

   private final List<CheckError> errors;

   public Checker() {
      checkers = new LinkedList<DataChecker<T>>();
      errors = new LinkedList<CheckError>();
   }

   /**
    * Specify the DataGetter which will give the object to be checked.
    * @param getter
    * @return
    */
   public static <T> Checker<T> with(DataGetter<T> getter) {
      Checker<T> checker = new Checker<T>();
      checker.getter = getter;
      return checker;
   }

   public Checker<T> addChecker(DataChecker<T> checker) {
      checkers.add(checker);
      return this;
   }

   public List<CheckError> check() {
      for (DataChecker<T> checker : checkers) {
         CheckError error = checker.check(getter.get());
         if (null != error) {
            errors.add(error);
         }
      }

      return errors;
   }
}
