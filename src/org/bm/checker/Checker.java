package org.bm.checker;

import java.util.LinkedList;
import java.util.List;

public class Checker<T> {

   private DataGetter<T> getter;

   private final List<DataChecker<T>> checkers;

   public Checker() {
      checkers = new LinkedList<DataChecker<T>>();
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

   public boolean check() {
      boolean result = false;
      for (DataChecker<T> checker : checkers) {
         result &= checker.check(getter.get());
      }

      return result;
   }
}
