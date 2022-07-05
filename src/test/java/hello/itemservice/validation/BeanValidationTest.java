package hello.itemservice.validation;

import hello.itemservice.domain.item.form.ItemForm;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemForm itemForm = new ItemForm();
        itemForm.setItemName(" ");
        itemForm.setPrice(0);
        itemForm.setQuantity(10000);

        Set<ConstraintViolation<ItemForm>> violations = validator.validate(itemForm);
        for (ConstraintViolation<ItemForm> violation : violations) {
            System.out.println("violation = " + violation);
        }
    }
}
