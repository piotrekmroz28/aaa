package piotrmroz.handlers;

import piotrmroz.dao.CategoryDao;
import piotrmroz.model.Category;
import piotrmroz.model.UserInputCommand;

import java.util.List;
import java.util.logging.Logger;

public class CategoryCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(CategoryCommandHandler.class.getName());

    private static final String COMMAND_NAME = "category";

    private CategoryDao categoryDao;

    public CategoryCommandHandler() {
        categoryDao = new CategoryDao();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("action can't be null");
        }

        switch (command.getAction()) {
            case LIST:
                LOG.info("List of categories...");

                if (!command.getParam().isEmpty()) {
                    throw new IllegalArgumentException("category list doesn't support any additional params");
                }

                List<Category> categories = categoryDao.findAll();
                categories.forEach(System.out::println);
                break;

            case ADD:
                LOG.info("Add category");

                if (command.getParam().size() != 1) {
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                String categoryName = command.getParam().get(0);
                categoryDao.add(new Category(categoryName));
                break;

            default: {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command: %s",
                        command.getAction(), command.getCommand()));
            }
        }
    }
}
