DESCRIPTION = "TeamViewer"
LICENSE = "CLOSED"
DEPENDS = "glibc alsa-lib libxext zlib libxfixes libxrender libsm libxtst freetype libxdamage libxau libxrandr"
PR = "r0"
SRC_URI += "${@bb.utils.contains("TUNE_FEATURES", "m64", "file://teamviewer_linux_x64.deb;subdir=teamviewer;name=m64", "", d)}"
SRC_URI += "${@bb.utils.contains("TUNE_FEATURES", "m32", "file://teamviewer_linux.deb;subdir=teamviewer;name=m32", "", d)}"
SRC_URI[m64.md5sum] = "decce9d21dbf99d508bb40c9a706e837"
SRC_URI[m64.sha256sum] = "b5ebf12e664c510f00062aeecc1ccd23e469323b4b620316fc791a958af971e9"
SRC_URI[m32.md5sum] = "377f2d47d11d7c0c664ed8b21dc188a8"
SRC_URI[m32.sha256sum] = "ef01b63eeec7aa9c046b613d26448b87f6bd00774941730fd26f60bb14f5aa75"
SRC_URI += "file://fix-readlink-parameter.patch \
            file://no_windowmanagercontrol.reg \
            file://extra-wine-registry-tweaks.patch \
"

S = "${WORKDIR}/teamviewer"

PACKAGES = "${PN}-dev ${PN}"

INHIBIT_PACKAGE_STRIP = "1"
# Disable architecture check (check to ensure binaries match the target architecture)
INSANE_SKIP_${PN} += "arch"
INSANE_SKIP_${PN}-dev += "arch"

# Suppress error caused by ${PN} depending on ${PN}-dev
INSANE_SKIP_${PN} += "dev-deps"

# Skip the unwanted steps
do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES_${PN} =  "/etc \
                /opt \
                /usr \
                /var \
"
FILES_${PN} += "${datadir}/applications/teamviewer-teamviewer9.desktop"
FILES_${PN}-dev+= "/opt/teamviewer9/tv_bin/wine/lib/*"

# Install the files to ${D}
do_install () {
    [ -d "${S}" ] || exit 1
    cd ${S} || exit 1
    tar --no-same-owner --exclude='./patches' --exclude='./.pc' -cpf - . \
        | tar --no-same-owner -xpf - -C ${D}
}

do_install_append () {
    # Remove all directories except required ones
    find ${D}/. -maxdepth 1 ! -name 'etc' ! -name 'opt' ! -name 'usr' ! -name 'var' ! -name '.*' | xargs rm -rf
    # Add extra registy tweak files
    install -m 0644 ${WORKDIR}/no_windowmanagercontrol.reg ${D}/opt/teamviewer9/tv_bin/script/
    # Install .desktop file
    install -d ${D}/${datadir}/applications
    install -m 0644 ${D}/opt/teamviewer9/tv_bin/desktop/teamviewer-teamviewer9.desktop ${D}/${datadir}/applications
}

pkg_postinst_${PN} () {
    TV_PACKAGE_INSTALL="yes"
    TV_SCRIPT_DIR="/opt/teamviewer9/tv_bin/script"

    source "$TV_SCRIPT_DIR/tvw_aux"
    source "$TV_SCRIPT_DIR/tvw_config"
    source "$TV_SCRIPT_DIR/tvw_daemon"

    #updateMenuEntries 'install'
    installDaemon
}

BBCLASSEXTEND = "multilib:lib32"
