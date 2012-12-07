package org.bm.checker;

import java.util.List;

import junit.framework.Assert;

import org.bm.checker.impl.IsNotNullAndNotEmptyDataChecker;
import org.bm.checker.impl.ValueInListDataChecker;
import org.junit.Test;

public class CheckerTest {

   @Test
   public void testCheck() {

      List<CheckError> checks = Checker.<Integer> with(new DataGetter<Integer>() {
         @Override
         public Integer get() {
            return 12;
         }
      }).addChecker(new DataChecker<Integer>() {
         @Override
         public CheckError check(Integer object) {
            if (object >= 10) {
               return new CheckError("Number is greater that 10 !");
            }

            return null;
         }
      }).check();

      Assert.assertEquals(1, checks.size()); // 1 error, because 12 >= 10 !
      System.out.println(checks.get(0).getMessage());
   }

   @Test
   public void testImpl() {
      List<CheckError> checks = Checker.<String> with(new DataGetter<String>() {
         @Override
         public String get() {
            return "5";
         }
      }).addChecker(new ValueInListDataChecker(true, "1", "3", "5")).check();

      Assert.assertEquals(0, checks.size()); // 0 error, because 5 is in the list
   }

   @Test
   public void testImpl2() {
      List<CheckError> checks = Checker.<String> with(new DataGetter<String>() {
         @Override
         public String get() {
            return "7";
         }
      }).addChecker(new ValueInListDataChecker(true, "1", "3", "5")).check();

      Assert.assertEquals(1, checks.size()); // 1 error, because 7 is not in the list
      System.out.println(checks.get(0).getMessage());
   }

   @Test
   public void testNullOrEmpty() {
      List<CheckError> checks = Checker.<String> with(new DataGetter<String>() {
         @Override
         public String get() {
            return null;
         }
      }).addChecker(new IsNotNullAndNotEmptyDataChecker()).check();

      Assert.assertEquals(1, checks.size()); // 1 error, becauseobject is null
      System.out.println(checks.get(0).getMessage());
   }
}
