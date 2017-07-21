package com.wearapay.lightning.uitls;

import android.content.Context;
import java.lang.reflect.Field;

/**
 * Created by Kindy on 2016-03-03.
 */
public class ResourceUtil {

  public static final int getAnimId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "anim", paramContext.getPackageName());
  }

  public static final int getArrayId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "array", paramContext.getPackageName());
  }

  public static final int getAttrId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "attr", paramContext.getPackageName());
  }

  public static final int getBoolId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "bool", paramContext.getPackageName());
  }

  public static final int getColorId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "color", paramContext.getPackageName());
  }

  public static final int getDimenId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "dimen", paramContext.getPackageName());
  }

  public static final int getDrawableId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "drawable", paramContext.getPackageName());
  }

  public static final int getIdId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "id", paramContext.getPackageName());
  }

  public static final int getIntegerId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "integer", paramContext.getPackageName());
  }

  public static final int getLayoutId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "layout", paramContext.getPackageName());
  }

  public static final int getMenuId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "menu", paramContext.getPackageName());
  }

  public static final int getRawId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "raw", paramContext.getPackageName());
  }

  public static final int getStringId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "string", paramContext.getPackageName());
  }

  public static final int getStyleId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "style", paramContext.getPackageName());
  }

  public static final int getXmlId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "xml", paramContext.getPackageName());
  }

  @Deprecated public static final int getStyleableId(Context paramContext, String paramString) {
    return paramContext.getResources()
        .getIdentifier(paramString, "styleable", paramContext.getPackageName());
  }

  @Deprecated public static final int[] getDeclareStyleableIntArray(Context context, String name) {
    try {
      //use reflection to access the resource class
      Field[] fields2 = Class.forName(context.getPackageName() + ".R$styleable").getFields();

      //browse all fields
      for (Field f : fields2) {
        //pick matching field
        if (f.getName().equals(name)) {
          //return as int array
          int[] ret = (int[]) f.get(null);
          return ret;
        }
      }
    } catch (Throwable t) {
    }

    return null;
  }

  public static int getIdByName(Context context, String className, String name) {
    String packageName = context.getPackageName();
    Class r = null;
    int id = 0;
    try {
      r = Class.forName(packageName + ".R");

      Class[] classes = r.getClasses();
      Class desireClass = null;

      for (int i = 0; i < classes.length; i++) {
        if (classes[i].getName().split("\\$")[1].equals(className)) {
          desireClass = classes[i];
          break;
        }
      }

      if (desireClass != null) id = desireClass.getField(name).getInt(desireClass);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    return id;
  }

  public static int[] getArryByName(Context context, String className, String name) {
    String packageName = context.getPackageName();
    Class r = null;
    int[] arr = null;
    try {
      r = Class.forName(packageName + ".R");

      Class[] classes = r.getClasses();
      Class desireClass = null;

      for (int i = 0; i < classes.length; i++) {
        if (classes[i].getName().split("\\$")[1].equals(className)) {
          desireClass = classes[i];
          break;
        }
      }

      if (desireClass != null) {
        Object value = desireClass.getField(name).get(desireClass);
        if (value.getClass().isArray()) arr = (int[]) value;
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    return arr;
  }
}
