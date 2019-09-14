from rest_framework.serializers import ModelSerializer
from .models import User, UserProfile, Event, Post, Trace


class UserProfileSerializer(ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('photo', 'bio', 'date_of_birth', 'country', 'city', 'points')


class UserSerializer(ModelSerializer):
    profile = UserProfileSerializer(required=True)

    class Meta:
        model = User
        fields = ('id', 'email', 'first_name', 'last_name', 'password', 'profile')
        extra_kwargs = {'password': {'write_only': True}}


class EventSerializer(ModelSerializer):
    class Meta:
        model = Event
        fields = ('tile', 'place_name', 'country', 'city', 'address', 'event_date', 'max_amount_of_people', 'is_acitve')


class PostSerializer(ModelSerializer):
    class Meta:
        model = Post
        fields = ('post_type', 'title', 'text')


class TraceSerializer(ModelSerializer):
    class Meta:
        model = Trace
        fields = ('time_start', 'time_finish', 'trace_length')
