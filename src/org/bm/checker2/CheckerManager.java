package org.bm.checker2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CheckerManager<C, R> {

   private final LinkedList<Checker<C, R>> checkList;

   private final Map<String, R> resultMap;

   private final Map<String, Object> dataMap;

   private Comparator<R> resultComparator = null;

   private CheckerManager() {
      checkList = new LinkedList<Checker<C, R>>();
      resultMap = new HashMap<String, R>();
      dataMap = new HashMap<String, Object>();
   }

   public static <C, R> CheckerManager<C, R> get() {
      return new CheckerManager<C, R>();
   }

   public void setComparator(Comparator<R> resultComparator) {
      this.resultComparator = resultComparator;
   }

   public CheckerManager<C, R> chain(Checker<C, R> checker) {
      if (!checkList.isEmpty()) {
         // tell the last checker he is not the last :)
         checkList.getLast().setNext(checker);
      }
      checkList.add(checker);
      return this;
   }

   public void addData(String key, Object value) {
      if (dataMap != null) {
         dataMap.put(key, value);
      }
   }

   public Object getData(String key) {
      if (dataMap != null) {
         return dataMap.get(key);
      }
      return null;
   }

   public List<R> check(C object) {
      dataMap.clear();
      if (checkList.isEmpty()) {
         return null;
      }

      Checker<C, R> checker = checkList.getFirst();
      checker.setCurrentDataMap(dataMap);
      checker.doCheckAndChain(object, resultMap);

      List<R> results = new ArrayList<R>(resultMap.values());
      if (resultComparator != null) {
         Collections.sort(results, resultComparator);
      }

      return results;
   }

   public int countChecks() {
      return checkList.size();
   }

}
