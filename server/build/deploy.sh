echo "*** Deploy .WAR file on server:"
scp server.war s_wilken@i43pc164.ipd.kit.edu:/home/stud/s_wilken/
echo "*** Restart server:"
ssh s_wilken@i43pc164.ipd.kit.edu
