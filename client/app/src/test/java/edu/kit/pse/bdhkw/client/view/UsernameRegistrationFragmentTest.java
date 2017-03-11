package edu.kit.pse.bdhkw.client.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableInteger;
import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.view.UsernameActivity;
import edu.kit.pse.bdhkw.client.view.UsernameRegistrationFragment;

import static edu.kit.pse.bdhkw.R.id.action0;
import static edu.kit.pse.bdhkw.R.id.cancel_action;
import static edu.kit.pse.bdhkw.R.id.next_registration_button;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Created by Schokomonsterchen on 06.03.2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UsernameRegistrationFragment.class)
public class UsernameRegistrationFragmentTest {

    @Mock
    private View viewMock;
    @Mock
    private EditText usernameMock;
    @Mock
    private UsernameRegistrationFragment usernameRegistrationFragmentMock;
    @Mock
    private AccountHandler accountHandlerMock;

    @Before
    public void setUp() throws Exception{
        viewMock = mock(View.class);
        usernameMock = mock(EditText.class);
        usernameRegistrationFragmentMock = spy(new UsernameRegistrationFragment(usernameMock));
        accountHandlerMock = mock(AccountHandler.class);

    }

    @Ignore
    public void onClickTestUsernameValid() throws Exception {
        PowerMockito.whenNew(AccountHandler.class).withAnyArguments().thenReturn(accountHandlerMock);
        doReturn(edu.kit.pse.bdhkw.R.id.next_registration_button).when(viewMock).getId();
        Editable editableMock = mock(Editable.class);
        doReturn(editableMock).when(usernameMock).getText();
        doReturn("Victoria").when(editableMock).toString();

        usernameRegistrationFragmentMock.onClick(viewMock);

        verify(accountHandlerMock, times(1)).registerUser(usernameRegistrationFragmentMock.getActivity(), "Victoria");
    }

    @Ignore
    public void onClickTestUsernameNotValid() throws Exception {
    //    UsernameActivity usernameActivityMock = PowerMockito.mock(UsernameActivity.class);
    //    when(usernameRegistrationFragmentMock.getActivityOfFrag()).thenReturn(usernameActivityMock);
    //    doReturn("test").when(usernameRegistrationFragmentMock).getStringForFrag(anyInt());
    //    PowerMockito.mockStatic(Toast.class);

        doReturn(edu.kit.pse.bdhkw.R.id.next_registration_button).when(viewMock).getId();
        Editable editableMock = mock(Editable.class);
        doReturn(editableMock).when(usernameMock).getText();
        doReturn("").when(editableMock).toString();

        usernameRegistrationFragmentMock.onClick(viewMock);

        verify(accountHandlerMock, times(0)).registerUser(usernameRegistrationFragmentMock.getActivity(), "Victoria");

    }

    @Ignore
    public void onClickTestCorrectUsername() throws Exception {
        PowerMockito.whenNew(AccountHandler.class).withAnyArguments().thenReturn(accountHandlerMock);
        doReturn(edu.kit.pse.bdhkw.R.id.next_registration_button).when(viewMock).getId();
        Editable editableMock = mock(Editable.class);
        doReturn(editableMock).when(usernameMock).getText();
        doReturn("Victoria   cool").when(editableMock).toString();

        usernameRegistrationFragmentMock.onClick(viewMock);

        //TODO: warum klappt das nicht? 0 statt 1!
        verify(accountHandlerMock, times(1)).registerUser(usernameRegistrationFragmentMock.getActivity(), "Victoria cool");
    }

    @Ignore
    public void onCreateViewTestReturnRightView() throws Exception {
        LayoutInflater inflaterMock = mock(LayoutInflater.class);
        ViewGroup containerMock = mock(ViewGroup.class);
        Bundle saveInstanceStateMock = mock(Bundle.class);
        PowerMockito.whenNew(View.class).withAnyArguments().thenReturn(viewMock);
        doReturn(viewMock).when(viewMock).findViewById(edu.kit.pse.bdhkw.R.id.next_registration_button);
        doReturn(usernameMock).when(usernameRegistrationFragmentMock).setUsername(any(View.class), anyInt());
        doNothing().when(usernameRegistrationFragmentMock).setButtonOnClickListener(any(View.class), anyInt());

        View view = usernameRegistrationFragmentMock.onCreateView(inflaterMock, containerMock, saveInstanceStateMock);

        //TODO warum tut das nicht? Logik falsch gedacht?
        assertEquals(view, viewMock);
    }

    @Test
    public void onAttachTest() throws Exception {
        Context contextMock = mock(Context.class);
        IntentFilter intentFilterMock = mock(IntentFilter.class);
        PowerMockito.whenNew(IntentFilter.class).withAnyArguments().thenReturn(intentFilterMock);
        BroadcastReceiver broadcastReceiverMock = mock(BroadcastReceiver.class);
        PowerMockito.whenNew(BroadcastReceiver.class).withAnyArguments().thenReturn(broadcastReceiverMock);
        Response responseMock = mock(Response.class);
        doReturn(true).when(responseMock).getSuccess();
        ObjectResponse objectResponseMock = mock(ObjectResponse.class);
        PowerMockito.when(responseMock).thenReturn(objectResponseMock);
        Editable editableMock = mock(Editable.class);
        doReturn(editableMock).when(usernameMock).getText();
        doReturn("Victoria").when(editableMock).toString();
        SerializableInteger serializableIntegerMock = mock(SerializableInteger.class);
        doReturn(serializableIntegerMock).when(objectResponseMock).getObject(anyString());
        doReturn(10).when(serializableIntegerMock.value);
        SimpleUser simpleUserMock = mock(SimpleUser.class);
        PowerMockito.whenNew(SimpleUser.class).withAnyArguments().thenReturn(simpleUserMock);
        doNothing().when(usernameRegistrationFragmentMock).saveSharedPreferences(anyString(), anyInt());
        doNothing().when(usernameRegistrationFragmentMock).getActivity().startActivity(new Intent(usernameRegistrationFragmentMock.getActivity(), GroupActivity.class));
        doNothing().when(usernameRegistrationFragmentMock).onDetach();
        doNothing().when(usernameRegistrationFragmentMock).onStop();

        LocalBroadcastManager localBroadcastManagerMock = mock(LocalBroadcastManager.class);

        verify(localBroadcastManagerMock, times(1)).getInstance(any(Context.class));
    }

}