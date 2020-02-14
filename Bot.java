import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot  {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi( );
        try{
            telegramBotsApi.registerBot(new Bot());
        }
        catch(TelegramApiException e) {
        e.printStackTrace();
        }
    }

    public void sendMsg(Message message,String text  ){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
             sendMessage(sendMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if(message!=null&&message.hasText()){
            switch (message.getText()){
                case "/Hello":
                    sendMsg(message,"Hello!");
                    break;
                case "/How are you?":
                    sendMsg(message,"Good, U?");
                    break;
                case "/U?":
                    sendMsg(message,"Answering some stupid questions");
                     break;
                case "/Good!":
                   sendMsg(message,":)");
               break;





                    default:
                   try{
                     sendMsg(message,Weather.getWeather(message.getText(),model));
                    } catch (IOException e) {
                       sendMsg(message, "Can't find this city");
                   }
            }
        }
    }
    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/Hello"));
        keyboardFirstRow.add(new KeyboardButton(("/How are you?")));
        keyboardFirstRow.add((new KeyboardButton("/Good!")));


        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }
    public String getBotUsername() {
        return "AIO";
    }

    public String getBotToken() {
        return "571998611:AAGV2wbWTOrKkD34KSrEt-zKpvFLeXReUMs" ;
    }
}
