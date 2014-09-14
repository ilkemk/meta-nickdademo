DESCRIPTION = "pvapi"
LICENSE = "CLOSED"
DEPENDS = "libtiff4 libpng12 libjpeg62 tcl tk atk glib-2.0 gdk-pixbuf gtk+ pango"
PR = "r0"
SRC_URI = "file://inc-pc/ImageLib.h \
        file://inc-pc/PvApi.h \
        file://inc-pc/PvRegIo.h \
        file://lib-pc/x64/4.5/libImagelib.a \
        file://lib-pc/x64/4.5/libPvAPI.a \
        file://bin-pc/x64/libPvAPI.so \
        file://bin-pc/x64/libPvJNI.so \
        file://bin-pc/x64/CLIpConfig \
        file://bin-pc/x64/SampleViewer \
        file://sampleviewer.desktop \
"
S = "${WORKDIR}"

PACKAGES =+ "${PN}-tools"

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN}-tools = "libdir"

do_install () {
	# Includes
	install -d ${D}/${includedir}
	install -m 0755 ${WORKDIR}/inc-pc/ImageLib.h ${D}/${includedir}/
	install -m 0755 ${WORKDIR}/inc-pc/PvApi.h ${D}/${includedir}/
	install -m 0755 ${WORKDIR}/inc-pc/PvRegIo.h ${D}/${includedir}/

   	# Libraries
	install -d ${D}/${libdir}
	install -m 0755 ${WORKDIR}/lib-pc/x64/4.5/libImagelib.a ${D}/${libdir}/
	install -m 0755 ${WORKDIR}/lib-pc/x64/4.5/libPvAPI.a ${D}/${libdir}/
	install -m 0755 ${WORKDIR}/bin-pc/x64/libPvAPI.so ${D}/${libdir}/
	install -m 0755 ${WORKDIR}/bin-pc/x64/libPvJNI.so ${D}/${libdir}/

	# Binaries
	install -d ${D}/${bindir}
	install -m 0755 ${WORKDIR}/bin-pc/x64/CLIpConfig ${D}/${bindir}/
	install -m 0755 ${WORKDIR}/bin-pc/x64/SampleViewer ${D}/${bindir}/

	# Add SampleViewer shortcut
	install -d ${D}/${datadir}
	install -d ${D}/${datadir}/applications
	install -m 0644 ${WORKDIR}/sampleviewer.desktop ${D}/${datadir}/applications
}

FILES_${PN}-tools += "${libdir}/libPvAPI.so"
FILES_${PN}-tools += "${libdir}/libPvJNI.so"
FILES_${PN}-tools += "${bindir}/CLIpConfig"
FILES_${PN}-tools += "${bindir}/SampleViewer"
