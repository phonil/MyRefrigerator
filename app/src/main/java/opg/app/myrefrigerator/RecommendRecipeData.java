package opg.app.myrefrigerator;

import android.widget.CheckBox;

public class RecommendRecipeData {

    private String ingredientName_tv, ingredientNumber_tv, ingredientDate_tv;
    boolean select_cb;


    public RecommendRecipeData(String ingredientName_tv, String ingredientNumber_tv, String ingredientDate_tv, boolean select_cb) {
        this.ingredientName_tv = ingredientName_tv;
        this.ingredientNumber_tv = ingredientNumber_tv;
        this.ingredientDate_tv = ingredientDate_tv;
        this.select_cb = select_cb;
    }

    public String getIngredientName_tv() {
        return ingredientName_tv;
    }

    public void setIngredientName_tv(String ingredientName_tv) {
        this.ingredientName_tv = ingredientName_tv;
    }

    public String getIngredientNumber_tv() {
        return ingredientNumber_tv;
    }

    public void setIngredientNumber_tv(String ingredientNumber_tv) {
        this.ingredientNumber_tv = ingredientNumber_tv;
    }

    public String getIngredientDate_tv() {
        return ingredientDate_tv;
    }

    public void setIngredientDate_tv(String ingredientDate_tv) {
        this.ingredientDate_tv = ingredientDate_tv;
    }

    public boolean isSelect_cb() {
        return select_cb;
    }

  /*  public CheckBox getSelect_cb(){

    }*/

    public void setSelect_cb(boolean select_cb) {
        this.select_cb = select_cb;
    }
}