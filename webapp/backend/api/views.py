from rest_framework import generics
from .serializers import UserSerializer, EventSerializer, PostSerializer, TrackSerializer
from .models import User, Event, Post, Track


class UserListView(generics.ListCreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class TrackView(generics.ListCreateAPIView):
    serializer_class = TrackSerializer

    def get_queryset(self):
        user = self.request.user.pk
        return Track.objects.filter(id_user=user)
