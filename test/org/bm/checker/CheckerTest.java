package org.bm.checker;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class CheckerTest {

   @Test
   public void testCheck() {

      List<CheckError<String>> checks = Checker.<Integer, String> get().addChecker(new DataChecker<Integer, String>() {
         @Override
         public CheckError<String> check(Integer object) {
            if (object >= 10) {
               return new CheckError<String>("Number is greater that 10 !");
            }

            return null;
         }
      }).check(new DataGetter<Integer>() {
         @Override
         public Integer get() {
            return 12;
         }
      });

      Assert.assertEquals(1, checks.size()); // 1 error, because 12 >= 10 !
      System.out.println(checks.get(0).getMessage());
   }

   @Test
   public void multiCheck() {

      final MultiData mdOK = new MultiData();
      mdOK.text = "Hello";
      mdOK.number = 32;
      mdOK.flag = 'Y';

      final MultiData mdWrong = new MultiData();
      mdWrong.text = "";
      mdWrong.number = -2;
      mdWrong.flag = 'J';

      Checker<MultiData, ReasonCode> checker = Checker.<MultiData, ReasonCode> get()
         .addChecker(new DataChecker<MultiData, ReasonCode>() {
            @Override
            public CheckError<ReasonCode> check(MultiData md) {
               if (md.text.isEmpty()) {
                  return new CheckError<ReasonCode>(ReasonCode._0001);
               }
               return null;
            };
         }).addChecker(new DataChecker<MultiData, ReasonCode>() {
            @Override
            public CheckError<ReasonCode> check(MultiData md) {
               if (md.number <= 0) {
                  return new CheckError<ReasonCode>(ReasonCode._0002);
               }
               return null;
            };
         }).addChecker(new DataChecker<MultiData, ReasonCode>() {
            @Override
            public CheckError<ReasonCode> check(MultiData md) {
               if (md.flag != 'Y' && md.flag != 'N') {
                  return new CheckError<ReasonCode>(ReasonCode._0003);
               }
               return null;
            };
         });

      List<CheckError<ReasonCode>> checks = checker.check(new DataGetter<MultiData>() {
         @Override
         public MultiData get() {
            return mdOK;
         }
      });

      Assert.assertEquals(0, checks.size());

      checks = checker.check(new DataGetter<MultiData>() {
         @Override
         public MultiData get() {
            return mdWrong;
         }
      });

      Assert.assertEquals(3, checks.size());
      for (CheckError<ReasonCode> c : checks) {
         System.out.println("Error : " + c.getMessage().getMessage());
      }
   }
}

class MultiData {
   String text;

   Integer number;

   Character flag;
};

class ReasonCode {
   public static final ReasonCode _0001 = new ReasonCode("0001 - Text empty");

   public static final ReasonCode _0002 = new ReasonCode("0002 - Negative number");

   public static final ReasonCode _0003 = new ReasonCode("0003 - not Y/N flag");

   public static final ReasonCode _0004 = new ReasonCode("0004");

   private final String m;

   public ReasonCode(String string) {
      m = string;
   }

   public String getMessage() {
      return m;
   }
};