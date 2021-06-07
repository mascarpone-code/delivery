package com.mascarpone.delivery.ws.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.payload.socket.GeneralSocketRequest;
import com.mascarpone.delivery.ws.exception.BadDataSocketException;
import com.mascarpone.delivery.ws.exception.UnknownActionException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketCommandHelper {
    private static final String BAD_DATA_FORMAT = "Invalid input data format";
    private static final String UNKNOWN_COMMAND = "Unknown command";

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<AccountActionType, GeneralSocketCommand> commands = new HashMap<>();
    private final BeanFactory beanFactory;

    public SocketCommandHelper(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    private void init() {
        commands.put(AccountActionType.AUTH, getCommandByQualifier(AccountActionType.AUTH.getAccountAction()));
    }

    public GeneralSocketCommand getCommand(TextMessage message) {
        try {
            GeneralSocketRequest request = mapper.readValue(message.getPayload(), GeneralSocketRequest.class);
            GeneralSocketCommand socketCommand = commands.get(request.getAccountActionType());

            if (socketCommand != null) {
                return commands.get(request.getAccountActionType());
            } else {
                throw new UnknownActionException(UNKNOWN_COMMAND);
            }
        } catch (UnknownActionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadDataSocketException(BAD_DATA_FORMAT, ex);
        }
    }

    private GeneralSocketCommand getCommandByQualifier(String qualifier) {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, GeneralSocketCommand.class, qualifier);
    }
}
