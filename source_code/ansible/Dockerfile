FROM alpine:latest

RUN apk add --no-cache \
    openssh \
    python3 \
    sudo \
    bash

# Create Ansible user
RUN adduser -D -s /bin/bash ansible \
    && echo "ansible:ansible" | chpasswd \
    && addgroup ansible wheel \
    && echo "ansible ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/ansible \
    && chmod 440 /etc/sudoers.d/ansible

# Configure SSH
RUN ssh-keygen -A \
    && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin no/' /etc/ssh/sshd_config

EXPOSE 22

CMD ["/usr/sbin/sshd", "-D", "-e"]
