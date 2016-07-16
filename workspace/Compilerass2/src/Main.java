import java.util.HashMap;

import syntaxtree.*;
import visitor.*;

public class Main {
	
   public static void main(String [] args) {
      try {
         Node root = new BuritoJavaParser(System.in).Goal();
         //System.out.println("Program parsed successfully");
         GJDepthFirst mytree=new GJDepthFirst<Tacout,Tempgen>();
         Tempgen tempvar = new Tempgen();
         System.out.println(((Tacout)root.accept(mytree,tempvar)).Tacopgm); // Your assignment part is invoked here.
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 