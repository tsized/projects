#!/usr/bin/env python2

"""
Author: Tim Sizemore
Date  : 9/26/12

A program that mirrors the fuctionality of finger.
"""

import sys
from socket import *

# The buffer size
SIZE     = 1024

# Default host name
HOSTNAME = 'localhost'

# Default port number
PORTNUM  = 79

def finger(connParams, query):
    """ Emulates the finger program
    Args:
        connParams - tuple with connection parameters.
        query      - query being sent over the network.
    """
    clientSocket = socket(AF_INET, SOCK_STREAM)
    clientSocket.connect(connParams)
    clientSocket.send(query)
    
    while 1:
        finResult = clientSocket.recv(SIZE)
        if not finResult:
            break
        print finResult

    clientSocket.close()

hostname = HOSTNAME
portnum  = PORTNUM
query    = '\n'
usage    = 'usage: finger <hostname> [<port>] [<user>]'

if len(sys.argv) == 2:
    hostname = sys.argv[1]
elif len(sys.argv) == 3:
    if ord((sys.argv[2])[0]) in range(48,58):
        hostname = sys.argv[1]
        portnum  = sys.argv[2]
    else:
        hostname = sys.argv[1]
        query    = sys.argv[2] + '\n'
elif len(sys.argv) == 4:
    hostname = sys.argv[1]
    portnum  = sys.argv[2]
    query    = sys.argv[3] + '\n'
else:
    print usage
    sys.exit(0)

try:
    finger((hostname, int(portnum)), query) 
except error, (errno, string):
    print 'socket error: ', string
    print usage
    sys.exit(errno)
except timeout:
    print 'timeout'
    print usage
    sys.exit(1)

sys.exit(0)
