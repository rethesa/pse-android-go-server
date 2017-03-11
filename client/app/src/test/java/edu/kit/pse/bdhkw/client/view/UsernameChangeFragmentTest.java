package edu.kit.pse.bdhkw.client.view;

import android.view.View;
import android.widget.EditText;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Schokomonsterchen on 07.03.2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UsernameChangeFragment.class)
public class UsernameChangeFragmentTest {

    @Mock
    private View viewMock;
    @Mock
    private UsernameChangeFragment usernameChangeFragmentMock;
    @Mock
    private UsernameActivity usernameActivityMock;
    @Mock
    private EditText usernameMock;

    @Before
    public void setUp() throws Exception {
        usernameActivityMock = mock(UsernameActivity.class);
        usernameMock = mock(EditText.class);
        usernameChangeFragmentMock = new UsernameChangeFragment(usernameMock);
        viewMock = mock(View.class);
    }


}