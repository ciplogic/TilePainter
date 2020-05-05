package hellofx.dialogs.atoms.common;

import hellofx.common.Utilities;
import javafx.scene.Parent;

public abstract class ViewBuilderBase
        <T extends ViewModelBase, TView extends Parent> {
    public T ViewModel;
    public TView View;

    public abstract void build();

    public void setup(Class<?> viewModelClass) {
        ViewModel = Utilities.newInstanceOf(viewModelClass);

        build();
        ViewModel.listen(this::onVmChanges);
    }

    public void onVmChanges(String propertyName) {

    }

}
